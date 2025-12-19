package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void insertAlunosView() throws Exception {
        MvcResult result = mockMvc.perform(get("/inserirAlunos"))
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName()).isEqualTo("Aluno/formAluno");
        assertThat(mv.getModel().containsKey("aluno")).isTrue();
    }

    @Test
    public void listarAlunosAdicionados() throws Exception {
        MvcResult result = mockMvc.perform(get("/alunos-adicionados"))
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName()).isEqualTo("Aluno/listAlunos");
        assertThat(mv.getModel().containsKey("alunosList")).isTrue();
    }

    @Test
    public void listaAlunosAtivos() throws Exception {
        MvcResult result = mockMvc.perform(get("/alunos-ativos"))
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName()).isEqualTo("Aluno/alunos-ativos");
        assertThat(mv.getModel().containsKey("alunosAtivos")).isTrue();
    }

    @Test
    public void removerAlunoRedireciona() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Aluno Remover");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("999999");
        serviceAluno.save(aluno);

        MvcResult result = mockMvc.perform(get("/remover/" + aluno.getId()))
                .andReturn();

        assertThat(result.getResponse().getRedirectedUrl()).isEqualTo("/alunos-adicionados");
    }
}
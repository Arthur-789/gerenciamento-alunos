package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoServiceTest {

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void getById() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(1L);
        Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
    }

    @Test
    public void salvarSemNome() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
                this.serviceAluno.save(aluno);});
    }

    @Test
    public void findAll() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Aluno 1");
        aluno1.setTurno(Turno.MATUTINO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("111111");
        serviceAluno.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Aluno 2");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.DIREITO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("222222");
        serviceAluno.save(aluno2);

        Assert.assertTrue(serviceAluno.findAll().size() >= 2);
    }

    @Test
    public void findByStatusAtivo() {
        Aluno ativo = new Aluno();
        ativo.setNome("Aluno ativo");
        ativo.setTurno(Turno.MATUTINO);
        ativo.setCurso(Curso.ADMINISTRACAO);
        ativo.setStatus(Status.ATIVO);
        ativo.setMatricula("892201");
        serviceAluno.save(ativo);

        Aluno inativo = new Aluno();
        inativo.setNome("Aluno inativo");
        inativo.setTurno(Turno.NOTURNO);
        inativo.setCurso(Curso.ADMINISTRACAO);
        inativo.setStatus(Status.INATIVO);
        inativo.setMatricula("892202");
        serviceAluno.save(inativo);

        Assert.assertTrue(
            serviceAluno.findByStatusAtivo()
                .stream()
                .allMatch(aluno -> aluno.getStatus() == Status.ATIVO)
        );
    }

    @Test
    public void deleteById() {
        Aluno aluno = new Aluno();
        aluno.setNome("Aluno para deletar");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("400289");
        serviceAluno.save(aluno);

        Long id = aluno.getId();
        serviceAluno.deleteById(id);

        Assert.assertThrows(Exception.class, () -> {
            serviceAluno.getById(id);
        });
    }

    @Test
    public void findByNomeContainingIgnoreCase() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Rodrigo Silva");
        aluno1.setTurno(Turno.MATUTINO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("177001");
        serviceAluno.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Rodrigo Oliveira");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.DIREITO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("177002");
        serviceAluno.save(aluno2);

        Assert.assertTrue(
            serviceAluno.findByNomeContainingIgnoreCase("rodrigo")
                .size() >= 2
        );
    }

}
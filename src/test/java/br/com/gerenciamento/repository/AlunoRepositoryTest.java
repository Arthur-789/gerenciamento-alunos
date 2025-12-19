package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void findByStatusAtivoTest() {
        Aluno alunoAtivo = new Aluno();
        alunoAtivo.setNome("Aluno ativo");
        alunoAtivo.setTurno(Turno.MATUTINO);
        alunoAtivo.setCurso(Curso.ADMINISTRACAO);
        alunoAtivo.setStatus(Status.ATIVO);
        alunoAtivo.setMatricula("1111");
        alunoRepository.save(alunoAtivo);

        List<Aluno> ativos = alunoRepository.findByStatusAtivo();
        Assert.assertTrue(ativos.stream().allMatch(a -> a.getStatus() == Status.ATIVO));
    }

    @Test
    public void findByStatusInativoTest() {
        Aluno alunoInativo = new Aluno();
        alunoInativo.setNome("Aluno inativo");
        alunoInativo.setTurno(Turno.NOTURNO);
        alunoInativo.setCurso(Curso.DIREITO);
        alunoInativo.setStatus(Status.INATIVO);
        alunoInativo.setMatricula("2222");
        alunoRepository.save(alunoInativo);

        List<Aluno> inativos = alunoRepository.findByStatusInativo();
        Assert.assertTrue(inativos.stream().allMatch(a -> a.getStatus() == Status.INATIVO));
    }

    @Test
    public void findByNomeContainingIgnoreCaseTest() {
        Aluno aluno = new Aluno();
        aluno.setNome("TestName");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.DIREITO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("3333");
        alunoRepository.save(aluno);

        List<Aluno> encontrados = alunoRepository.findByNomeContainingIgnoreCase("testname");
        Assert.assertTrue(encontrados.size() > 0);
    }

    @Test
    public void findAllTest() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Aluno 1");
        aluno1.setTurno(Turno.MATUTINO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("4444");
        alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Aluno 2");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.DIREITO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("5555");
        alunoRepository.save(aluno2);

        List<Aluno> todos = alunoRepository.findAll();
        Assert.assertTrue(todos.size() >= 2);
    }
}
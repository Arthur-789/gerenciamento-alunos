package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void saveUsuarioValidoTest() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@email.com");
        usuario.setUser("usuario1");
        usuario.setSenha("123456");
        usuarioRepository.save(usuario);

        List<Usuario> encontrados = usuarioRepository.findAll();
        Assert.assertTrue(encontrados.size() > 0);
    }

    @Test
    public void findByEmailTest() {
        Usuario usuario = new Usuario();
        usuario.setEmail("email@teste.com");
        usuario.setUser("usuario2");
        usuario.setSenha("123456");
        usuarioRepository.save(usuario);

        Usuario encontrado = usuarioRepository.findByEmail("email@teste.com");
        Assert.assertNotNull(encontrado);
        Assert.assertEquals("usuario2", encontrado.getUser());
    }

    @Test
    public void findByUserAndSenhaTest() {
        Usuario usuario = new Usuario();
        usuario.setEmail("login@teste.com");
        usuario.setUser("loginUser");
        usuario.setSenha("senha123");
        usuarioRepository.save(usuario);

        Usuario encontrado = usuarioRepository.buscarLogin("loginUser", "senha123");
        Assert.assertNotNull(encontrado);
        Assert.assertEquals("loginUser", encontrado.getUser());
    }
}
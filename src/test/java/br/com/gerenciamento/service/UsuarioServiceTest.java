package br.com.gerenciamento.service;

import br.com.gerenciamento.model.Usuario;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void salvarComEmailExistente() throws Exception {
        Usuario usuario1 = new Usuario();
        usuario1.setEmail("teste@email.com");
        usuario1.setUser("usuario1");
        usuario1.setSenha("123456");
        serviceUsuario.salvarUsuario(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setEmail("teste@email.com");
        usuario2.setUser("usuario2");
        usuario2.setSenha("123456");

        Assert.assertThrows(Exception.class, () -> {
            serviceUsuario.salvarUsuario(usuario2);
        });
    }

    @Test
    public void salvarSemUser() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste2@email.com");
        usuario.setSenha("123456");

        serviceUsuario.salvarUsuario(usuario);

        Usuario buscado = serviceUsuario.loginUser(null, "123456");
        Assert.assertNull("O usuário não deveria ter sido salvo", buscado);
    }

    @Test
    public void salvarUsuarioValido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("valido@email.com");
        usuario.setUser("usuarioValido");
        usuario.setSenha("123456");

        serviceUsuario.salvarUsuario(usuario);

        Usuario buscado = serviceUsuario.loginUser("usuarioValido", usuario.getSenha());
        Assert.assertNotNull("O usuário válido deveria ser salvo", buscado);
    }

    @Test
    public void loginInvalido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setEmail("login@email.com");
        usuario.setUser("usuarioLogin");
        usuario.setSenha("123456");
        serviceUsuario.salvarUsuario(usuario);

        Usuario errado = serviceUsuario.loginUser("usuarioLogin", "senhaErrada");
        Assert.assertNull("Login com senha incorreta deve retornar null", errado);

        Usuario inexistente = serviceUsuario.loginUser("usuarioInexistente", "123456");
        Assert.assertNull("Login de usuário inexistente deve retornar null", inexistente);
    }
}
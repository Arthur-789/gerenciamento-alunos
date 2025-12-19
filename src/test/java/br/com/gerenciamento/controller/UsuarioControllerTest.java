package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
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
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void loginView() throws Exception {
        MvcResult result = mockMvc.perform(get("/"))
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName()).isEqualTo("login/login");
        assertThat(mv.getModel().containsKey("usuario")).isTrue();
    }

    @Test
    public void cadastroView() throws Exception {
        MvcResult result = mockMvc.perform(get("/cadastro"))
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName()).isEqualTo("login/cadastro");
        assertThat(mv.getModel().containsKey("usuario")).isTrue();
    }

    @Test
    public void logoutInvalidaSessao() throws Exception {
        MvcResult result = mockMvc.perform(post("/logout"))
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        assertThat(mv.getViewName()).isEqualTo("login/login");
    }

    @Test
    public void salvarUsuarioRedireciona() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("testeInt");
        usuario.setEmail("testeInt@teste.com");
        usuario.setSenha("1234");

        MvcResult result = mockMvc.perform(post("/salvarUsuario")
                        .flashAttr("usuario", usuario))
                .andReturn();

        assertThat(result.getResponse().getRedirectedUrl()).isEqualTo("/");
    }
}
package com.example.application.views.cliente;

import com.example.application.repositories.ClienteRepository;
import com.example.application.repositories.postgres.ClienteRepositoryImpl;

import com.example.application.entidades.Cliente;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Cliente")
@Route(value = "Cliente", layout = MainLayout.class)
public class ClienteView extends VerticalLayout {

    private TextField nomeField;
    private TextField emailField;
    private TextField cpfField;
    private TextField enderecoField;
    private Button salvarButton;
    private Button limpabutton;
    private Grid<Cliente> grid = new Grid<>(Cliente.class, false);
    private ClienteRepository repository = new ClienteRepositoryImpl();
    private Boolean teste = true;
    private int id;

    public ClienteView() {

        add(new Paragraph("CADASTRO DO CLIENTE"));
        nomeField = new TextField("Nome:");
        emailField = new TextField("Email:");
        cpfField = new TextField("CPF:");
        enderecoField = new TextField("Endereco:");
        salvarButton = new Button("Salvar");
        limpabutton = new Button("Cancelar");

        limpabutton.addClickListener(ev -> {
            nomeField.setValue("");
            emailField.setValue("");
            cpfField.setValue("");
            enderecoField.setValue("");
        });

        salvarButton.addClickListener(ev -> {

            String nm = nomeField.getValue();
            String em = emailField.getValue();
            String cf = cpfField.getValue();
            String ed = enderecoField.getValue();

            if ((nm == "") && (nm == "") && (em == "") && (cf == "") && (ed == "")) {
                Notification.show("Campos vazios!");
                nomeField.focus();
                emailField.focus();
                emailField.focus();
                cpfField.focus();
                enderecoField.focus();
                return;
            }
            if (nm == "") {
                Notification.show("Nome invalido!");
                nomeField.focus();
                return;
            }
            if (em == "") {
                Notification.show("Email invalido!");
                emailField.focus();
                return;
            }
            if (cf == "") {
                Notification.show("CPF invalido!");
                cpfField.focus();
                return;
            }
            if (ed == "") {
                Notification.show("Endereco invalido!");
                enderecoField.focus();
                return;
            }

            Cliente novo = new Cliente();
            novo.setNome(nomeField.getValue());
            novo.setEmail(emailField.getValue());
            novo.setCpf(cpfField.getValue());
            novo.setEndereco(enderecoField.getValue());

            if (teste == true) {
                repository.inserir(novo);
                Notification.show("Cadastro concluido!!");
            } else {
                novo.setId_cliente(id);
                repository.editar(novo);
                Notification.show("Alteração salva!");
                teste = true;
            }
            grid.setItems(repository.listar());
            limpabutton.click();
        });
        salvarButton.addClickShortcut(Key.ENTER);

        HorizontalLayout hl = new HorizontalLayout();
        hl.add(salvarButton, limpabutton);

        grid.addColumn(Cliente::getNome)
                .setHeader("Nome");
        grid.addColumn(Cliente::getEmail)
                .setHeader("Email");
        grid.addColumn(Cliente::getCpf)
                .setHeader("CPF");
        grid.addColumn(Cliente::getEndereco)
                .setHeader("Endereco");

        grid.addComponentColumn(c -> {
            Button editaButton = new Button("Editar");
            editaButton.addClickListener(ev -> {
                nomeField.focus();
                nomeField.setValue(c.getNome());
                emailField.setValue(c.getEmail());
                cpfField.setValue(c.getCpf());
                enderecoField.setValue(c.getEndereco());
                id = c.getId_cliente();
                teste = false;
            });
            return editaButton;
        });

        grid.addComponentColumn(c -> {
            Button del = new Button();
            del.setIcon(new Icon(VaadinIcon.TRASH));
            del.addClickListener(ev -> {
                repository.remover(c.getId_cliente());
                grid.setItems(repository.listar());
            });
            return del;
        });

        grid.setItems(repository.listar());
        add(nomeField, emailField, cpfField, enderecoField, hl, grid);
    }
}

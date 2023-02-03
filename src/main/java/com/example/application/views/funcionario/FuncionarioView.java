package com.example.application.views.funcionario;

import com.example.application.entidades.Funcionario;
import com.example.application.repositories.FuncionarioRepository;
import com.example.application.repositories.postgres.FuncionarioRepositoryImpl;
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
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Funcionario")
@Route(value = "Funcionario", layout = MainLayout.class)
public class FuncionarioView extends VerticalLayout {

    private TextField nomeField;
    private NumberField salarioField;
    private TextField cpfField;
    private TextField categoriaField;
    private Button salvarButton;
    private Button limpabutton;
    private Grid<Funcionario> grid = new Grid<>(Funcionario.class, false);
    private FuncionarioRepository repository = new FuncionarioRepositoryImpl();
    private Boolean teste = true;
    private int id;

    public FuncionarioView() {

        add(new Paragraph("CADASTRO DO FUNCIONARIO"));
        nomeField = new TextField("Nome:");
        categoriaField = new TextField("Categoria:");
        cpfField = new TextField("CPF:");
        salarioField = new NumberField("Salario::");
        limpabutton = new Button("Cancelar");
        salvarButton = new Button("Salvar");

        limpabutton.addClickListener(ev -> {
            nomeField.setValue("");
            salarioField.setValue(null);
            cpfField.setValue("");
            categoriaField.setValue("");
        });

        salvarButton.addClickListener(ev -> {

            String nm = nomeField.getValue();
            String ca = categoriaField.getValue();
            String cf = cpfField.getValue();
            Double sl = salarioField.getValue();

            if ((nm == "") && (ca == "") && (cf == "") && (sl == null || sl <= 0)) {
                Notification.show("Campos vazios!");
                nomeField.focus();
                categoriaField.focus();
                cpfField.focus();
                salarioField.focus();
                return;
            }
            if (nm == "") {
                Notification.show("Nome invalido!");
                nomeField.focus();
                return;
            }
            if (ca == "") {
                Notification.show("Categoria invalida!");
                categoriaField.focus();
                return;
            }
            if (cf == "") {
                Notification.show("CPF invalido!");
                cpfField.focus();
                return;
            }
            if (sl == null || sl <= 0) {
                Notification.show("Salario invalido!");
                salarioField.focus();
                return;
            }

            Funcionario novo = new Funcionario();
            novo.setNome(nomeField.getValue());
            novo.setSalario(salarioField.getValue());
            novo.setCPF(cpfField.getValue());
            novo.setCategoria(categoriaField.getValue());

            if (teste == true) {
                repository.inserir(novo);
                Notification.show("Funcionario Cadastrado!!");
            } else {
                novo.setId_funcionario(id);
                repository.editar(novo);
                Notification.show("Alteração no Funcionairo concluida!");
                teste = true;
            }
            grid.setItems(repository.listar());
            limpabutton.click();
            Notification.show("Cadastro concluido ");
        });

        salvarButton.addClickShortcut(Key.ENTER);
        HorizontalLayout hl = new HorizontalLayout();
        hl.add(salvarButton, limpabutton);

        grid.addColumn(Funcionario::getNome)
                .setHeader("Nome");
        grid.addColumn(Funcionario::getCategoria)
                .setHeader("Categoria");
        grid.addColumn(Funcionario::getCPF)
                .setHeader("CPF");
        grid.addColumn(Funcionario::getSalario)
                .setHeader("Salario");

        grid.addComponentColumn(c -> {
            Button editaButton = new Button("Editar");
            editaButton.addClickListener(ev -> {
                nomeField.focus();
                nomeField.setValue(c.getNome());
                categoriaField.setValue(c.getCategoria());
                cpfField.setValue(c.getCPF());
                salarioField.setValue(c.getSalario());
                id = c.getId_funcionario();
                teste = false;
            });
            return editaButton;
        });

        grid.addComponentColumn(c -> {
            Button del = new Button();
            del.setIcon(new Icon(VaadinIcon.TRASH));
            del.addClickListener(ev -> {
                repository.remover(c.getId_funcionario());
                grid.setItems(repository.listar());
            });
            return del;
        });
        grid.setItems(repository.listar());
        add(nomeField, categoriaField, cpfField, salarioField, hl, grid);
    }
}

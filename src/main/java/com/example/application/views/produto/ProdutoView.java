package com.example.application.views.produto;

import com.example.application.entidades.Produto;
import com.example.application.repositories.ProdutoRepository;
import com.example.application.repositories.postgres.ProdutoRepositoryImpl;
import com.example.application.views.MainLayout;
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

@PageTitle("Produto")
@Route(value = "Produto", layout = MainLayout.class)

public class ProdutoView extends VerticalLayout {

    private TextField nomeField;
    private NumberField valorField;
    private TextField codigoField;
    private Button salvarButton;
    private Button limpabutton;
    private Grid<Produto> grid = new Grid<>(Produto.class, false);
    private ProdutoRepository repository = new ProdutoRepositoryImpl();
    private Boolean teste = true;
    private int id;

    public ProdutoView() {

        add(new Paragraph("CADASTRO DO PRODUTO"));
        nomeField = new TextField("Nome:");
        valorField = new NumberField("Valor:");
        codigoField = new TextField("Codigo:");
        limpabutton = new Button("Cancelar");
        salvarButton = new Button("Salvar");

        limpabutton.addClickListener(ev -> {
            nomeField.setValue("");
            valorField.setValue(null);
            codigoField.setValue("");
        });

        salvarButton.addClickListener(ev -> {

            String nm = nomeField.getValue();
            Double vl = valorField.getValue();
            String cd = codigoField.getValue();

            if ((nm == "") && (vl == null || vl <= 0) && (cd == "")) {
                Notification.show("Campos vazios!");
                nomeField.focus();
                valorField.focus();
                codigoField.focus();
                return;
            }
            if (nm == "") {
                Notification.show("Nome invalido!");
                nomeField.focus();
                return;
            }
            if (vl == null || vl <= 0) {
                Notification.show("Valor invalido!");
                valorField.focus();
                return;
            }
            if (cd == "") {
                Notification.show("Codigo invalido!");
                codigoField.focus();
                return;
            }

            Produto novo = new Produto();
            novo.setNome(nomeField.getValue());
            novo.setValor(valorField.getValue());
            novo.setCodigo(codigoField.getValue());

            if (teste == true) {
                repository.inserir(novo);
                Notification.show("Produto salvo!!");
            } else {
                novo.setId_produto(id);
                repository.editar(novo);
                Notification.show("Alteração no Produto salva!");
                teste = true;
            }
            grid.setItems(repository.listar());
            limpabutton.click();
            Notification.show("Cadastro concluido ");

            limpabutton.click();
        });

        HorizontalLayout hl = new HorizontalLayout();
        hl.add(salvarButton, limpabutton);

        grid.addColumn(Produto::getNome)
                .setHeader("Nome");
        grid.addColumn(Produto::getValor)
                .setHeader("Valor");
        grid.addColumn(Produto::getCodigo)
                .setHeader("Codigo");

        grid.addComponentColumn(c -> {
            Button editaButton = new Button("Editar");
            editaButton.addClickListener(ev -> {
                nomeField.focus();
                nomeField.setValue(c.getNome());
                valorField.setValue(c.getValor());
                codigoField.setValue(c.getCodigo());
                id = c.getId_produto();
                teste = false;
            });
            return editaButton;
        });

        grid.addComponentColumn(c -> {
            Button del = new Button();
            del.setIcon(new Icon(VaadinIcon.TRASH));
            del.addClickListener(ev -> {
                repository.remover(c.getId_produto());
                grid.setItems(repository.listar());
            });
            return del;
        });
        grid.setItems(repository.listar());
        add(nomeField, valorField, codigoField, hl, grid);
    }
}

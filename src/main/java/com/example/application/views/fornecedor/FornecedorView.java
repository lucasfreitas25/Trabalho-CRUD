package com.example.application.views.fornecedor;

import java.time.LocalDate;
import com.example.application.entidades.Fornecedor;
import com.example.application.repositories.FornecedorRepository;
import com.example.application.repositories.postgres.FornecedorRepositoryImpl;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Fornecedor")
@Route(value = "Fornecedor", layout = MainLayout.class)
public class FornecedorView extends VerticalLayout {

    private TextArea pedidoField;
    private Button salvarButton;
    private Button limpabutton;
    private DatePicker dataField;
    private Grid<Fornecedor> grid = new Grid<>(Fornecedor.class, false);
    private FornecedorRepository repository = new FornecedorRepositoryImpl();
    private Boolean teste = true;
    private int id;

    public FornecedorView() {

        add(new Paragraph("PEDIDO PRO FORNECEDOR"));
        pedidoField = new TextArea("Pedido");
        dataField = new DatePicker("Data para entrega");
        salvarButton = new Button("Salvar");
        limpabutton = new Button("Cancelar");

        limpabutton.addClickListener(ev -> {
            pedidoField.setValue("");
            dataField.setValue(null);
        });

        salvarButton.addClickListener(ev -> {

            String pd = pedidoField.getValue();
            LocalDate dt = dataField.getValue();

            if ((pd == "") && (dt == null)) {
                Notification.show("Campos vazios!");
                pedidoField.focus();
                dataField.focus();
                return;
            }
            if (pd == "") {
                Notification.show("Pedido invalido !");
                pedidoField.focus();
                return;
            }
            if (dt == null) {
                Notification.show("Data invalida!");
                dataField.focus();
                return;
            }

            Fornecedor novo = new Fornecedor();
            novo.setPedido(pedidoField.getValue());
            novo.setData(dataField.getValue());

            if (teste == true) {
                repository.inserir(novo);
                Notification.show("Salvo!!");
            } else {
                novo.setId_fornecedor(id);
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

        grid.addColumn(Fornecedor::getPedido)
                .setHeader("Pedido");
        grid.addColumn(Fornecedor::getData)
                .setHeader("Data de entrega");

        grid.addComponentColumn(c -> {
            Button editaButton = new Button("Editar");
            editaButton.addClickListener(ev -> {
                pedidoField.focus();
                pedidoField.setValue(c.getPedido());
                dataField.setValue(c.getData());
                id = c.getId_fornecedor();
                teste = false;
            });
            return editaButton;
        });

        grid.addComponentColumn(c -> {
            Button del = new Button();
            del.setIcon(new Icon(VaadinIcon.TRASH));
            del.addClickListener(ev -> {
                repository.remover(c.getId_fornecedor());
                grid.setItems(repository.listar());
            });
            return del;
        });
        grid.setItems(repository.listar());
        add(pedidoField, dataField, hl, grid);
    }
}

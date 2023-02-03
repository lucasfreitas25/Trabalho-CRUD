package com.example.application.views.venda;

import java.time.LocalDate;

import com.example.application.entidades.Venda;
import com.example.application.repositories.VendaRepository;
import com.example.application.repositories.postgres.VendaRepositoryImpl;
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
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.select.Select;

@PageTitle("Venda")
@Route(value = "Venda", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class VendaView extends VerticalLayout {

    private TextArea pedidoField;
    private TextField cpfField;
    private Select<String> entregaField;
    private NumberField valortotalField;
    // private IntegerField pedidoField;
    private DatePicker dataField;
    private Button salvarButton;
    private Button limpabutton;
    private Grid<Venda> grid = new Grid<>(Venda.class, false);
    private VendaRepository repository = new VendaRepositoryImpl();
    private Boolean teste = true;
    private int id;

    public VendaView() {

        add(new Paragraph("Central de Vendas"));
        cpfField = new TextField("Digite o CPF do Cliente");
        valortotalField = new NumberField("Valor total");
        pedidoField = new TextArea("Pedido do cliente");
        entregaField = new Select<>();
        entregaField.setLabel("Forma de entrega");
        entregaField.setItems("Presencial", "Delivery");
        dataField = new DatePicker("Data da Venda");
        salvarButton = new Button("Salvar");
        limpabutton = new Button("Cancelar");

        limpabutton.addClickListener(ev -> {
            cpfField.setValue("");
            pedidoField.setValue("");
            entregaField.setValue("");
            valortotalField.setValue(null);
            dataField.setValue(null);
        });

        salvarButton.addClickListener(ev -> {

            String cp = cpfField.getValue();
            String pd = pedidoField.getValue();
            String et = entregaField.getValue();
            Double vt = valortotalField.getValue();
            LocalDate dt = dataField.getValue();
            boolean CpfValido = false;

            CpfValido = repository.verificador(cp);
            if (!CpfValido) {
                Notification.show("Cliente nao cadastrado!");
                cpfField.focus();
                return;
            }
            if ((cp == "") && (vt == null || vt <= 0) && (pd == "") && (et == "") && (cp == "") && (dt == null)) {
                Notification.show("Campos vazios!");
                cpfField.focus();
                pedidoField.focus();
                entregaField.focus();
                valortotalField.focus();
                dataField.focus();
                return;
            }
            if (pd == null) {
                Notification.show("Quantidade invalida !");
                pedidoField.focus();
                return;
            }
            if (et == "") {
                Notification.show("Entrega invalida!");
                entregaField.focus();
                return;
            }

            if (cp == "") {
                Notification.show("CPF invalido !");
                cpfField.focus();
                return;
            }
            if (vt == null || vt <= 0) {
                Notification.show("Valor Invalido!");
                valortotalField.focus();
                return;
            }
            if (dt == null) {
                Notification.show("Data invalida!");
                dataField.focus();
                return;
            }

            Venda novo = new Venda();
            novo.setCpf(cpfField.getValue());
            novo.setEntrega(entregaField.getValue());
            novo.setPedido(pedidoField.getValue());
            novo.setValortotal(valortotalField.getValue());
            novo.setData(dataField.getValue());
            if (teste == true) {
                repository.inserir(novo);
                Notification.show("Venda concluida!!");
            } else {
                novo.setId_venda(id);
                repository.editar(novo);
                Notification.show("Alteração na Venda salva!");
                teste = true;
            }
            grid.setItems(repository.listar());
        });

        salvarButton.addClickShortcut(Key.ENTER);
        HorizontalLayout hl = new HorizontalLayout();
        hl.add(salvarButton, limpabutton);

        grid.addColumn(Venda::getCpf)
                .setHeader("CPF");
        grid.addColumn(Venda::getPedido)
                .setHeader("Pedido");
        grid.addColumn(Venda::getEntrega)
                .setHeader("Forma de entrega");
        grid.addColumn(Venda::getValortotal)
                .setHeader("Valor total");
        grid.addColumn(Venda::getData)
                .setHeader("Data da venda");

        grid.addComponentColumn(c -> {
            Button editaButton = new Button("Editar");
            editaButton.addClickListener(ev -> {
                cpfField.focus();
                cpfField.setValue(c.getCpf());
                pedidoField.setValue(c.getPedido());
                entregaField.setValue(c.getEntrega());
                dataField.setValue(c.getData());
                id = c.getId_venda();
                teste = false;
            });
            return editaButton;
        });

        grid.addComponentColumn(c -> {
            Button del = new Button();
            del.setIcon(new Icon(VaadinIcon.TRASH));
            del.addClickListener(ev -> {
                repository.remover(c.getId_venda());
                grid.setItems(repository.listar());
            });
            return del;
        });

        grid.setItems(repository.listar());
        add(cpfField, pedidoField, valortotalField, dataField, entregaField, hl, grid);

    }
}

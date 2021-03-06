package com.ddoerr.clientgui.widgets.functional;

import com.ddoerr.clientgui.attachments.ContainerAttachment;
import com.ddoerr.clientgui.bindings.BindingUtil;
import com.ddoerr.clientgui.models.Axis;
import com.ddoerr.clientgui.models.Insets;
import com.ddoerr.clientgui.widgets.Widget;
import com.ddoerr.clientgui.widgets.layout.AnchorWidget;
import com.ddoerr.clientgui.widgets.layout.CardWidget;
import com.ddoerr.clientgui.widgets.layout.DynamicStackWidget;
import com.ddoerr.clientgui.widgets.visual.LabelWidget;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import net.minecraft.text.OrderedText;

public class TabWidget extends Widget<TabWidget> {
    protected final ContainerAttachment containerAttachment = new ContainerAttachment(focusListeners.fire());

    protected final ListProperty<Widget<?>> tabTitles = new SimpleListProperty<>(this, "tabTitles", FXCollections.observableArrayList());
    protected final ListProperty<Widget<?>> tabContents = new SimpleListProperty<>(this, "tabContents", FXCollections.observableArrayList());

    private CardWidget cardWidget;

    public TabWidget() {
        attach(containerAttachment);
        containerAttachment.addChild(build());
    }

    protected Widget<?> build() {
        return new DynamicStackWidget()
                .setAxis(Axis.Vertical)
                .addChild(
                        new DynamicStackWidget()
                                .setAxis(Axis.Horizontal)
                                .Do(w -> w.childrenProperty().bindContent(
                                        BindingUtil.map(tabTitles, c ->
                                                new ButtonWidget().setChild(c)
                                                    .setMargin(Insets.of(1))
                                                    .Do(t -> t.sizeProperty().bind(sizeProperty().setHeight(20).divideWidth(tabTitles.sizeProperty()).addInnerInsets(t.marginProperty())))
                                                    .Do(t -> t.activeProperty().bind(selectedIndexProperty().isEqualTo(tabTitles.indexOf(c))))
                                                    .addActionListener((event) -> setSelectedIndex(tabTitles.indexOf(c)))
                                        )
                                ))
                )
                .addChild(
                        cardWidget = new CardWidget()
                                .Do(c -> {
                                    c.childrenProperty().bindContent(tabContents);
                                    c.sizeProperty().bind(sizeProperty().addHeight(-20));
                                })
                );
    }

    public TabWidget addTab(OrderedText tabTitle, Widget<?> tab) {
        tabTitles.add(new LabelWidget().setText(tabTitle).attach(AnchorWidget.Anchor.MiddleCenter));
        tabContents.add(tab);
        return this;
    }

    public IntegerProperty selectedIndexProperty() {
        return cardWidget.selectedIndexProperty();
    }
    public TabWidget setSelectedIndex(int selectedIndex) {
        cardWidget.setSelectedIndex(selectedIndex);
        return this;
    }
    public int getSelectedIndex() {
        return cardWidget.getSelectedIndex();
    }
}

package com.ddoerr.clientgui.widgets.visual;

import com.ddoerr.clientgui.models.Point;
import com.ddoerr.clientgui.widgets.Widget;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ImageWidget extends Widget<ImageWidget> {
    protected ObjectProperty<Identifier> texture = new SimpleObjectProperty<>(this, "texture");

    public ObjectProperty<Identifier> textureProperty() {
        return texture;
    }
    public Identifier getTexture() {
        return texture.get();
    }
    public void setTexture(Identifier texture) {
        this.texture.set(texture);
    }

    @Override
    public void render(MatrixStack matrixStack, Point mouse) {
        MinecraftClient.getInstance().getTextureManager().bindTexture(texture.get());
        DrawableHelper.drawTexture(matrixStack, (int) position.get().getX(), (int) position.get().getY(), size.get().getWidth(), size.get().getHeight(),
                0, 0, size.get().getWidth(), size.get().getHeight(), size.get().getWidth(), size.get().getHeight());
    }
}

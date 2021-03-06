package dev.fiki.forgehax.main.util.draw;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import lombok.Getter;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;

import java.util.Map;

@Getter
public class BufferProvider {
  private final IRenderTypeBuffer.Impl bufferSource;
  private final BufferBuilderEx defaultBuffer = new BufferBuilderEx(Tessellator.getInstance());

  public BufferProvider() {
    BufferMap buffers = new BufferMap()
        .add(RenderTypeEx.glLines())
        .add(RenderTypeEx.glTriangle())
        .add(RenderTypeEx.glQuads())
        .add(RenderTypeEx.blockTranslucentCull())
        .add(RenderTypeEx.blockCutout())
        .add(RenderType.getGlint())
        .add(RenderType.getEntityGlint())
        ;

    this.bufferSource = IRenderTypeBuffer.getImpl(buffers.build(), defaultBuffer);
  }

  public BufferBuilderEx getBuffer(RenderType renderType) {
    return (BufferBuilderEx) getBufferSource().getBuffer(renderType);
  }

  private static class BufferMap {
    Map<RenderType, BufferBuilder> buffers = new Object2ObjectLinkedOpenHashMap<>();

    public BufferMap add(RenderType type) {
      buffers.put(type, new BufferBuilderEx(new BufferBuilder(type.getBufferSize())));
      return this;
    }

    public Map<RenderType, BufferBuilder> build() {
      return buffers;
    }
  }
}

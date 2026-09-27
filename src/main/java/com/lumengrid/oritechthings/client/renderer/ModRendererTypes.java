//package com.lumengrid.oritechthings.client.renderer;
//
//import com.mojang.blaze3d.vertex.DefaultVertexFormat;
//import com.mojang.blaze3d.vertex.VertexFormat;
//import net.minecraft.client.renderer.rendertype.RenderStateShard;
//import net.minecraft.client.renderer.rendertype.RenderSetup;
//import net.minecraft.client.renderer.rendertype.RenderType;
//
//import java.util.OptionalDouble;
//
//import static net.minecraft.client.renderer.rendertype.RenderStateShard.*;
//
//public abstract class ModRendererTypes extends RenderType {
//
//    public static final RenderType BLOCK_LINES;
//
//
//    private ModRendererTypes(String name, RenderSetup setup) {
//        super(name, setup);
//    }
//
//    static {
//
//        RenderSetup setup = RenderSetup.builder()
//                .setShaderState(RENDERTYPE_LINES_SHADER)
//                .setLineState(new RenderStateShard.LineStateShard(OptionalDouble.of(7.0F)))
//                .setLayeringState(VIEW_OFFSET_Z_LAYERING)
//                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
//                .setOutputState(ITEM_ENTITY_TARGET)
//                .setWriteMaskState(COLOR_DEPTH_WRITE)
//                .setCullState(NO_CULL)
//                .setDepthTestState(NO_DEPTH_TEST)
//                .createRenderSetup(false);
//
//        BLOCK_LINES = create(
//                "lumen_block_lines",
//                DefaultVertexFormat.POSITION_COLOR,
//                VertexFormat.Mode.LINES,
//                256,
//                false,
//                false,
//                setup
//        );
//    }
//}
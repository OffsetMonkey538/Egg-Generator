package top.offsetmonkey538.egggenerator;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import top.offsetmonkey538.egggenerator.block.entity.AbstractEggGeneratorBlockEntity;
import top.offsetmonkey538.egggenerator.block.entity.Tier4EggGeneratorBlockEntity;

public class EggGeneratorBlockEntityRenderer<T extends AbstractEggGeneratorBlockEntity> implements BlockEntityRenderer<T> {
    private ChickenEntity chicken;
    private World world;

    public EggGeneratorBlockEntityRenderer(@SuppressWarnings("unused") BlockEntityRendererFactory.Context context) {

    }

    @Override
    @SuppressWarnings("DataFlowIssue")
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (this.world == null) this.world = entity.getWorld();
        if (this.chicken == null) {
            this.chicken = EntityType.CHICKEN.create(world);
            this.chicken.setAiDisabled(true);
        }

        float rotation = 0;
        boolean isTier4 = entity instanceof Tier4EggGeneratorBlockEntity;
        if (isTier4) rotation = (entity.getWorld().getTime() + tickDelta) * 64;

        matrices.push();

        matrices.translate(0.5f, 0.315f, 0.5f);
        matrices.scale(0.5f, 0.5f, 0.5f);
        matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(entity.getCachedState().get(Properties.HORIZONTAL_FACING).asRotation() + rotation));

        MinecraftClient.getInstance().getEntityRenderDispatcher().render(chicken, 0, 0, 0, 0, isTier4 ? tickDelta : 0, matrices, vertexConsumers, light);

        matrices.pop();
    }
}

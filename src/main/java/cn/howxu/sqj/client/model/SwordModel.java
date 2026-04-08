package cn.howxu.sqj.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;

public class SwordModel extends Model {
    private final ModelPart root;

    public SwordModel(ModelPart root) {
        super(RenderType::entityCutout);
        this.root = root.getChild("root");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create()
                        .texOffs(0, 54).addBox(-1.5F, -23.0F, -1.5F, 3.0F, 12.0F, 3.0F)
                        .texOffs(49, 53).addBox(-2.5F, -11.0F, -2.5F, 5.0F, 2.0F, 5.0F)
                        .texOffs(0, 0).addBox(0.0F, -66.0F, -6.5F, 0.0F, 40.0F, 13.0F)
                        .texOffs(27, 0).addBox(-0.5F, -66.0F, -3.5F, 1.0F, 40.0F, 7.0F),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        root.addOrReplaceChild("blade", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-0.5F, -2.5F, -2.5F, 1.0F, 5.0F, 5.0F, new CubeDeformation(-0.01F)),
                PartPose.offsetAndRotation(0.0F, -66.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        root.addOrReplaceChild("blade2", CubeListBuilder.create()
                        .texOffs(35, 39).addBox(0.0F, -4.5F, -4.5F, 0.0F, 9.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, -66.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        root.addOrReplaceChild("blade_mid", CubeListBuilder.create()
                        .texOffs(22, 53).addBox(-1.5F, -2.5F, -2.5F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.5F))
                        .texOffs(54, 48).addBox(-1.0F, -0.5F, -5.5F, 2.0F, 1.0F, 3.0F)
                        .texOffs(27, 48).addBox(-1.0F, 2.5F, -0.5F, 2.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -25.8F, 0.0F, 0.7854F, 0.0F, 0.0F));

        root.addOrReplaceChild("handle_core", CubeListBuilder.create()
                        .texOffs(45, 35).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.7F)),
                PartPose.offsetAndRotation(0.0F, -4.5F, 0.0F, -0.7854F, 0.0F, 0.0F));

        root.addOrReplaceChild("core", CubeListBuilder.create()
                        .texOffs(44, 24).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F),
                PartPose.offset(0.0F, -4.5F, 0.0F));

        root.addOrReplaceChild("upper_guard", CubeListBuilder.create()
                        .texOffs(44, 12).addBox(-1.0F, -3.0F, 4.0F, 2.0F, 4.0F, 1.0F)
                        .texOffs(37, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F)
                        .texOffs(42, 58).addBox(-0.5F, -1.0F, 4.0F, 1.0F, 1.0F, 4.0F)
                        .texOffs(60, 40).addBox(-0.5F, -2.0F, 3.0F, 1.0F, 1.0F, 6.0F)
                        .texOffs(56, 12).addBox(-0.5F, -3.0F, 3.0F, 1.0F, 1.0F, 7.0F)
                        .texOffs(60, 21).addBox(-0.5F, -6.0F, 6.0F, 1.0F, 1.0F, 6.0F)
                        .texOffs(13, 54).addBox(-0.5F, -7.0F, 10.0F, 1.0F, 1.0F, 2.0F)
                        .texOffs(44, 12).addBox(-0.5F, -5.0F, 3.0F, 1.0F, 2.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, -25.5F, 0.9F, -0.1745F, 0.0F, 0.0F));

        root.addOrReplaceChild("lower_guard", CubeListBuilder.create()
                        .texOffs(27, 0).addBox(-1.0F, -3.0F, -4.0F, 2.0F, 4.0F, 1.0F)
                        .texOffs(14, 0).addBox(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 4.0F)
                        .texOffs(14, 7).addBox(-0.5F, -1.0F, -6.0F, 1.0F, 1.0F, 4.0F)
                        .texOffs(33, 58).addBox(-0.5F, -2.0F, -8.0F, 1.0F, 1.0F, 6.0F)
                        .texOffs(56, 0).addBox(-0.5F, -3.0F, -9.0F, 1.0F, 1.0F, 7.0F)
                        .texOffs(13, 58).addBox(-0.5F, -6.0F, -11.0F, 1.0F, 1.0F, 6.0F)
                        .texOffs(46, 0).addBox(-0.5F, -7.0F, -11.0F, 1.0F, 1.0F, 2.0F)
                        .texOffs(44, 0).addBox(-0.5F, -5.0F, -11.0F, 1.0F, 2.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, -25.5F, -1.9F, 0.1745F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}
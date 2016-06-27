package fossilsarcheology.client.model.base;

import fossilsarcheology.client.render.entity.RenderPrehistoric;
import fossilsarcheology.server.entity.EntityPrehistoric;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelBase;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class ModelPrehistoric extends AdvancedModelBase {
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        doAdvancedStuff(true);
        renderAll();
        if (entity instanceof EntityPrehistoric) {
            EntityPrehistoric mob = (EntityPrehistoric) entity;
            if (mob.isSkeleton()) {
                renderFossil(mob, f, f1, f2, f3, f4, f5);
            } else {
                renderLiving(mob, f, f1, f2, f3, f4, f5);
            }
        }
    }

    /**
     * PreRender and setAngles
     */
    public void renderFossil(EntityPrehistoric entity, float f, float f1, float f2, float f3, float f4, float f5) {
    }

    /**
     * PreRender and setAngles
     */
    public void renderLiving(EntityPrehistoric entity, float f, float f1, float f2, float f3, float f4, float f5) {
    }

    /**
     * PreRender and setAngles
     */
    public void renderSleeping(EntityPrehistoric entity, float f, float f1, float f2, float f3, float f4, float f5) {
    }

    public void renderHeldItem(RenderPrehistoric renderPrehistoric, EntityLivingBase entity, float i) {
    }

    public void renderAll() {
        for (Object element : this.boxList) {
            if (element instanceof AdvancedModelRenderer) {
                AdvancedModelRenderer box = (AdvancedModelRenderer) element;
                if (box.getParent() == null) {
                    box.render(0.0625F);
                }
            }
        }
    }

    public void doAdvancedStuff(boolean reset) {
        for (Object element : this.boxList) {
            if (element instanceof AdvancedModelRenderer) {
                AdvancedModelRenderer box = (AdvancedModelRenderer) element;
                if (reset) {
                    box.resetToDefaultPose();
                } else {
                    box.updateDefaultPose();
                }
            }
        }
    }

    public void setRotateAngle(AdvancedModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

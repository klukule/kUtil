package klukule.core;

import klukule.core.ObfHelper;
import java.lang.reflect.Method;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class EntityHelperBase {

   public static MovingObjectPosition getEntityLook(EntityLiving ent, double d) {
      return getEntityLook(ent, d, false);
   }

   public static MovingObjectPosition getEntityLook(EntityLiving ent, double d, boolean ignoreEntities) {
      if(ent == null) {
         return null;
      } else {
         double d1 = d;
         float f = 1.0F;
         MovingObjectPosition mop = rayTrace(ent, d, f);
         Vec3 vec3d = getPosition(ent, f);
         if(mop != null) {
            d1 = mop.hitVec.distanceTo(vec3d);
         }

         if(d1 > d) {
            d1 = d;
         }

         Vec3 vec3d1 = ent.getLook(f);
         Vec3 vec3d2 = vec3d.addVector(vec3d1.xCoord * d1, vec3d1.yCoord * d1, vec3d1.zCoord * d1);
         if(!ignoreEntities) {
            Entity entity1 = null;
            float f1 = 1.0F;
            List list = ent.worldObj.getEntitiesWithinAABBExcludingEntity(ent, ent.boundingBox.addCoord(vec3d1.xCoord * d1, vec3d1.yCoord * d1, vec3d1.zCoord * d1).expand((double)f1, (double)f1, (double)f1));
            double d2 = 0.0D;

            for(int i = 0; i < list.size(); ++i) {
               Entity entity = (Entity)list.get(i);
               if(entity.canBeCollidedWith()) {
                  float f2 = entity.getCollisionBorderSize();
                  AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double)f2, (double)f2, (double)f2);
                  MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3d, vec3d2);
                  if(axisalignedbb.isVecInside(vec3d)) {
                     if(0.0D < d2 || d2 == 0.0D) {
                        entity1 = entity;
                        d2 = 0.0D;
                     }
                  } else if(movingobjectposition != null) {
                     double d3 = vec3d.distanceTo(movingobjectposition.hitVec);
                     if(d3 < d2 || d2 == 0.0D) {
                        entity1 = entity;
                        d2 = d3;
                     }
                  }
               }
            }

            if(entity1 != null) {
               mop = new MovingObjectPosition(entity1);
            }
         }

         return mop;
      }
   }

   public static Vec3 getPosition(Entity ent, float par1) {
      if(par1 == 1.0F) {
         return ent.worldObj.getWorldVec3Pool().getVecFromPool(ent.posX, ent.posY + (double)ent.getEyeHeight(), ent.posZ);
      } else {
         double var2 = ent.prevPosX + (ent.posX - ent.prevPosX) * (double)par1;
         double var4 = ent.prevPosY + (double)ent.getEyeHeight() + (ent.posY - ent.prevPosY) * (double)par1;
         double var6 = ent.prevPosZ + (ent.posZ - ent.prevPosZ) * (double)par1;
         return ent.worldObj.getWorldVec3Pool().getVecFromPool(var2, var4, var6);
      }
   }

   public static MovingObjectPosition rayTrace(EntityLiving ent, double par1, float par3) {
      Vec3 var4 = getPosition(ent, par3);
      Vec3 var5 = ent.getLook(par3);
      Vec3 var6 = var4.addVector(var5.xCoord * par1, var5.yCoord * par1, var5.zCoord * par1);
      return ent.worldObj.rayTraceBlocks(var4, var6);
   }

   public static MovingObjectPosition rayTrace(World world, Vec3 vec3d, Vec3 vec3d1, boolean flag, boolean flag1, boolean goThroughTransparentBlocks) {
      return rayTrace(world, vec3d, vec3d1, flag, flag1, goThroughTransparentBlocks, 200);
   }

   public static MovingObjectPosition rayTrace(World world, Vec3 vec3d, Vec3 vec3d1, boolean flag, boolean flag1, boolean goThroughTransparentBlocks, int distance) {
      if(!Double.isNaN(vec3d.xCoord) && !Double.isNaN(vec3d.yCoord) && !Double.isNaN(vec3d.zCoord)) {
         if(!Double.isNaN(vec3d1.xCoord) && !Double.isNaN(vec3d1.yCoord) && !Double.isNaN(vec3d1.zCoord)) {
            int i = MathHelper.floor_double(vec3d1.xCoord);
            int j = MathHelper.floor_double(vec3d1.yCoord);
            int k = MathHelper.floor_double(vec3d1.zCoord);
            int l = MathHelper.floor_double(vec3d.xCoord);
            int i1 = MathHelper.floor_double(vec3d.yCoord);
            int j1 = MathHelper.floor_double(vec3d.zCoord);
            int k1 = world.getBlockId(l, i1, j1);
            int i2 = world.getBlockMetadata(l, i1, j1);
            Block block = Block.blocksList[k1];
            if((!flag1 || block == null || block.getCollisionBoundingBoxFromPool(world, l, i1, j1) != null) && k1 > 0 && block.canCollideCheck(i2, flag)) {
               MovingObjectPosition l1 = block.collisionRayTrace(world, l, i1, j1, vec3d, vec3d1);
               if(l1 != null) {
                  return l1;
               }
            }

            int var44 = distance;

            while(var44-- >= 0) {
               if(Double.isNaN(vec3d.xCoord) || Double.isNaN(vec3d.yCoord) || Double.isNaN(vec3d.zCoord)) {
                  return null;
               }

               if(l == i && i1 == j && j1 == k) {
                  return null;
               }

               boolean flag2 = true;
               boolean flag3 = true;
               boolean flag4 = true;
               double d = 999.0D;
               double d1 = 999.0D;
               double d2 = 999.0D;
               if(i > l) {
                  d = (double)l + 1.0D;
               } else if(i < l) {
                  d = (double)l + 0.0D;
               } else {
                  flag2 = false;
               }

               if(j > i1) {
                  d1 = (double)i1 + 1.0D;
               } else if(j < i1) {
                  d1 = (double)i1 + 0.0D;
               } else {
                  flag3 = false;
               }

               if(k > j1) {
                  d2 = (double)j1 + 1.0D;
               } else if(k < j1) {
                  d2 = (double)j1 + 0.0D;
               } else {
                  flag4 = false;
               }

               double d3 = 999.0D;
               double d4 = 999.0D;
               double d5 = 999.0D;
               double d6 = vec3d1.xCoord - vec3d.xCoord;
               double d7 = vec3d1.yCoord - vec3d.yCoord;
               double d8 = vec3d1.zCoord - vec3d.zCoord;
               if(flag2) {
                  d3 = (d - vec3d.xCoord) / d6;
               }

               if(flag3) {
                  d4 = (d1 - vec3d.yCoord) / d7;
               }

               if(flag4) {
                  d5 = (d2 - vec3d.zCoord) / d8;
               }

               boolean byte0 = false;
               byte var45;
               if(d3 < d4 && d3 < d5) {
                  if(i > l) {
                     var45 = 4;
                  } else {
                     var45 = 5;
                  }

                  vec3d.xCoord = d;
                  vec3d.yCoord += d7 * d3;
                  vec3d.zCoord += d8 * d3;
               } else if(d4 < d5) {
                  if(j > i1) {
                     var45 = 0;
                  } else {
                     var45 = 1;
                  }

                  vec3d.xCoord += d6 * d4;
                  vec3d.yCoord = d1;
                  vec3d.zCoord += d8 * d4;
               } else {
                  if(k > j1) {
                     var45 = 2;
                  } else {
                     var45 = 3;
                  }

                  vec3d.xCoord += d6 * d5;
                  vec3d.yCoord += d7 * d5;
                  vec3d.zCoord = d2;
               }

               Vec3 vec3d2 = world.getWorldVec3Pool().getVecFromPool(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);
               l = (int)(vec3d2.xCoord = (double)MathHelper.floor_double(vec3d.xCoord));
               if(var45 == 5) {
                  --l;
                  ++vec3d2.xCoord;
               }

               i1 = (int)(vec3d2.yCoord = (double)MathHelper.floor_double(vec3d.yCoord));
               if(var45 == 1) {
                  --i1;
                  ++vec3d2.yCoord;
               }

               j1 = (int)(vec3d2.zCoord = (double)MathHelper.floor_double(vec3d.zCoord));
               if(var45 == 3) {
                  --j1;
                  ++vec3d2.zCoord;
               }

               int j2 = world.getBlockId(l, i1, j1);
               if(!goThroughTransparentBlocks || !isTransparent(j2)) {
                  int k2 = world.getBlockMetadata(l, i1, j1);
                  Block block1 = Block.blocksList[j2];
                  if((!flag1 || block1 == null || block1.getCollisionBoundingBoxFromPool(world, l, i1, j1) != null) && j2 > 0 && block1.canCollideCheck(k2, flag)) {
                     MovingObjectPosition movingobjectposition1 = block1.collisionRayTrace(world, l, i1, j1, vec3d, vec3d1);
                     if(movingobjectposition1 != null) {
                        return movingobjectposition1;
                     }
                  }
               }
            }

            return null;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   public static boolean hasFuel(InventoryPlayer inventory, int itemID, int damage, int amount) {
      if(amount <= 0) {
         return true;
      } else {
         int amountFound = 0;

         for(int var3 = 0; var3 < inventory.mainInventory.length; ++var3) {
            if(inventory.mainInventory[var3] != null && inventory.mainInventory[var3].itemID == itemID && inventory.mainInventory[var3].getItemDamage() == damage) {
               amountFound += inventory.mainInventory[var3].stackSize;
               if(amountFound >= amount) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public static boolean isTransparent(int i) {
      return Block.lightOpacity[i] != 255;
   }

   public static boolean isLookingAtMoon(World world, EntityLiving ent, float renderTick, boolean goThroughTransparentBlocks) {
      if(ent.dimension != -1 && ent.dimension != 1) {
         double de = 2.71828183D;
         float f = world.getCelestialAngle(1.0F);
         if((double)f >= 0.26D && (double)f <= 0.74D) {
            float var10000;
            if(f > 0.5F) {
               var10000 = f - 0.5F;
            } else {
               var10000 = 0.5F - f;
            }

            float f3 = ent.rotationYaw > 0.0F?270.0F:-90.0F;
            f3 = f > 0.5F?(ent.rotationYaw > 0.0F?90.0F:-270.0F):f3;
            f = f > 0.5F?1.0F - f:f;
            if((double)f <= 0.475D) {
               de = 2.71828183D;
            } else if((double)f <= 0.4875D) {
               de = 3.88377D;
            } else if((double)f <= 0.4935D) {
               de = 4.91616D;
            } else if((double)f <= 0.4965D) {
               de = 5.40624D;
            } else if((double)f <= 0.5D) {
               de = 9.8D;
            }

            boolean yawCheck = (double)(ent.rotationYaw % 360.0F) <= Math.pow(de, 4.92574D * (double)world.getCelestialAngle(1.0F)) + (double)f3 && (double)(ent.rotationYaw % 360.0F) >= -Math.pow(de, 4.92574D * (double)world.getCelestialAngle(1.0F)) + (double)f3;
            float ff = world.getCelestialAngle(1.0F);
            ff = ff > 0.5F?1.0F - ff:ff;
            ff -= 0.26F;
            ff = ff / 0.26F * -94.0F - 4.0F;
            boolean pitchCheck = ent.rotationPitch <= ff + 2.5F && ent.rotationPitch >= ff - 2.5F;
            Vec3 vec3d = getPosition(ent, renderTick);
            Vec3 vec3d1 = ent.getLook(renderTick);
            Vec3 vec3d2 = vec3d.addVector(vec3d1.xCoord * 500.0D, vec3d1.yCoord * 500.0D, vec3d1.zCoord * 500.0D);
            boolean mopCheck = rayTrace(ent.worldObj, vec3d, vec3d2, true, false, goThroughTransparentBlocks, 500) == null;
            return yawCheck && pitchCheck && mopCheck;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public static boolean consumeInventoryItem(InventoryPlayer inventory, int itemID, int damage, int amount) {
      if(amount <= 0) {
         return true;
      } else {
         int amountFound = 0;

         int var3;
         for(var3 = 0; var3 < inventory.mainInventory.length; ++var3) {
            if(inventory.mainInventory[var3] != null && inventory.mainInventory[var3].itemID == itemID && inventory.mainInventory[var3].getItemDamage() == damage) {
               amountFound += inventory.mainInventory[var3].stackSize;
               if(amountFound >= amount) {
                  break;
               }
            }
         }

         if(amountFound >= amount) {
            for(var3 = 0; var3 < inventory.mainInventory.length; ++var3) {
               if(inventory.mainInventory[var3] != null && inventory.mainInventory[var3].itemID == itemID && inventory.mainInventory[var3].getItemDamage() == damage) {
                  while(amount > 0 && inventory.mainInventory[var3] != null && inventory.mainInventory[var3].stackSize > 0) {
                     --amount;
                     --inventory.mainInventory[var3].stackSize;
                     if(inventory.mainInventory[var3].stackSize <= 0) {
                        inventory.mainInventory[var3] = null;
                     }

                     if(amount <= 0) {
                        return true;
                     }
                  }
               }
            }
         }

         return false;
      }
   }

   public static void forceJump(EntityLiving ent) {
      try {
         Class e = ent.getClass();
         Method var11 = e.getDeclaredMethod(ObfHelper.obfuscation?"bl":"jump", new Class[0]);
         var11.setAccessible(true);
         var11.invoke(ent, (Object[])null);
      } catch (NoSuchMethodException var3) {
         ent.motionY = 0.41999998688697815D;
         if(ent.isPotionActive(Potion.jump)) {
            ent.motionY += (double)((float)(ent.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
         }

         if(ent.isSprinting()) {
            float var1 = ent.rotationYaw * 0.017453292F;
            ent.motionX -= (double)(MathHelper.sin(var1) * 0.2F);
            ent.motionZ += (double)(MathHelper.cos(var1) * 0.2F);
         }

         ent.isAirBorne = true;
         ForgeHooks.onLivingJump(ent);
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   public static String getDeathSound(EntityLiving ent) {
      String s = "";

      try {
         Class e = ent.getClass();
         Method m = e.getDeclaredMethod(ObfHelper.obfuscation?"bd":"getDeathSound", new Class[0]);
         m.setAccessible(true);
         s = (String)m.invoke(ent, (Object[])null);
      } catch (Exception var4) {
         ;
      }

      return s;
   }

   public static float updateRotation(float oriRot, float intendedRot, float maxChange) {
      float var4 = MathHelper.wrapAngleTo180_float(intendedRot - oriRot);
      if(var4 > maxChange) {
         var4 = maxChange;
      }

      if(var4 < -maxChange) {
         var4 = -maxChange;
      }

      return oriRot + var4;
   }

   public static void setVelocity(Entity entity, double d, double d1, double d2) {
      if(entity != null) {
         entity.motionX = d;
         entity.motionY = d1;
         entity.motionZ = d2;
      }
   }
}

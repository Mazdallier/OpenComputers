package li.cil.oc.common.multipart

import codechicken.lib.vec.BlockCoord
import codechicken.multipart.MultiPartRegistry.{IPartConverter, IPartFactory}
import codechicken.multipart.{TMultiPart, MultiPartRegistry}
import li.cil.oc.Blocks
import li.cil.oc.common.block.Delegator
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge

object MultiPart extends IPartFactory with IPartConverter {
  def init() {
    MultiPartRegistry.registerConverter(this)
    MultiPartRegistry.registerParts(this, Array("oc:cable"))
    MinecraftForge.EVENT_BUS.register(new EventHandler())
  }

  override def createPart(name: String, client: Boolean): TMultiPart = {
    if (name.equals("oc:cable"))
      return new CablePart
    null
  }

  override def canConvert(blockID: Int): Boolean = {
    blockID == Blocks.cable.parent.blockID
  }

  override def convert(world: World, pos: BlockCoord): TMultiPart = {
    Delegator.subBlock(world, pos.x, pos.y, pos.z) match {
      case Some(subBlock) =>
        if (subBlock == Blocks.cable)
          return new CablePart()
      case _ =>
    }
    null
  }
}
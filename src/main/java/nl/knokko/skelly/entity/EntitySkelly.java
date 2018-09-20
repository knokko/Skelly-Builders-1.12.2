package nl.knokko.skelly.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import nl.knokko.util.blocks.BlockType;
import nl.knokko.util.blocks.BlockTypes;

public class EntitySkelly extends EntityLiving {
	
	public static final String ID = "skellyblack";

	public EntitySkelly(World world) {
		super(world);
	}
	
	@Override
	protected void initEntityAI(){
		this.tasks.addTask(0, new AI(this));
	}
	
	@Override
	protected void applyEntityAttributes(){
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1);
	}
	
	public boolean isSwingingArms() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static class AI extends EntityAIBase {
		
		private static BlockType[] WALL_BLOCKS = {/*Blocks.STONEBRICK, Blocks.PLANKS, Blocks.SANDSTONE, Blocks.NETHER_BRICK*/};
		private static BlockType[] FLOOR_BLOCKS = {/*Blocks.COBBLESTONE, Blocks.PLANKS, Blocks.SANDSTONE, Blocks.NETHER_BRICK*/};
		private static BlockType[] ROOF_BLOCKS = {/*Blocks.PLANKS, Blocks.SANDSTONE, Blocks.NETHER_BRICK*/};
		private static BlockType[] FLAT_ROOF_BLOCKS = {/*Blocks.STONEBRICK, Blocks.PLANKS, Blocks.SANDSTONE, Blocks.NETHER_BRICK*/};
		
		static {
			registerWallBlocks(BlockTypes.WOODEN_PLANKS);
			registerWallBlocks(new BlockType(Blocks.STONEBRICK, 0));
		}
		
		private static BlockType[] registerBlocks(BlockType[] array, BlockType...blocks){
			List<BlockType> blocksToAdd = new ArrayList<BlockType>(blocks.length);
			for(BlockType block : blocks){
				boolean add = true;
				for(BlockType building : array){
					if(block.equals(building)){
						add = false;
						break;
					}
				}
				if(add)
					blocksToAdd.add(block);
			}
			int index = array.length;
			array = Arrays.copyOf(array, array.length + blocksToAdd.size());
			for(BlockType block : blocksToAdd)
				array[index++] = block;
			return array;
		}
		
		public static void registerWallBlocks(BlockType...blocks){
			WALL_BLOCKS = registerBlocks(WALL_BLOCKS, blocks);
		}
		
		public static void registerFloorBlocks(BlockType...blocks){
			FLOOR_BLOCKS = registerBlocks(FLOOR_BLOCKS, blocks);
		}
		
		public static void registerRoofBlocks(BlockType...blocks){
			ROOF_BLOCKS = registerBlocks(ROOF_BLOCKS, blocks);
		}
		
		public static void registerFlatRoofBlocks(BlockType...blocks){
			FLAT_ROOF_BLOCKS = registerBlocks(FLAT_ROOF_BLOCKS, blocks);
		}
		
		protected final EntitySkelly skelly;
		protected final MoveState mover;
		
		protected State state;
		
		public AI(EntitySkelly skelly){
			this.skelly = skelly;
			this.mover = new MoveState(skelly);
			state = State.CHOOSING_STRUCTURE;
		}

		@Override
		public boolean shouldExecute() {
			return true;
		}
		
		@Override
		public void updateTask(){
			mover.update();
		}
		
		public EntitySkelly getEntity(){
			return skelly;
		}
		
		private static enum State {
			
			CHOOSING_STRUCTURE,
			INVENTORISING,
			CHOOSING_BLOCK,
			SEARCHING_FOR_BLOCK,
			TAKING_BLOCK,
			PLACING_BLOCK;
			
			/*
			 * Choose structure
			 * for(BlockType bt : struct.blockTypes){
			 *     inventorise
			 *     choose block
			 *     collect blocks
			 *     place blocks
			 * }
			 * 
			 * finished?
			 */
		}
		
		private static class MoveState {
			
			private static final double REQUIRED_DISTANCE_SQRT = 9;
			
			private boolean isMoving;
			private boolean isMovingToBlock;
			
			private int targetX;
			private int targetY;
			private int targetZ;
			
			private final EntitySkelly skelly;
			
			private MoveState(EntitySkelly skelly){
				this.skelly = skelly;
			}
			
			private double speed(){
				return 1;
			}
			
			private void update(){
				if(isMoving){
					double dx = targetX - skelly.posX;
					double dy = targetY - skelly.posY;
					double dz = targetZ - skelly.posZ;
					if(dx * dx + dy * dy + dz * dz <= REQUIRED_DISTANCE_SQRT){
						skelly.getMoveHelper().action = EntityMoveHelper.Action.WAIT;
						isMoving = false;
						isMovingToBlock = false;
					}
				}
			}
			
			private void setDestination(int x, int y, int z){
				targetX = x;
				targetY = y;
				targetZ = z;
				isMovingToBlock = true;
				skelly.getMoveHelper().setMoveTo(x, y, z, speed());
				isMoving = true;
			}
		}
		
		private static class BlockMap {
			
		}
		
		private static class RecipeMap {
			
		}
	}
}

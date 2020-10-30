package today.exusiai.utils.entity;

import net.minecraft.entity.Entity;

@FunctionalInterface
public interface ICheck {

   /***
    * @author autumn
    * @param entity
    */
   boolean validate(Entity entity);
}

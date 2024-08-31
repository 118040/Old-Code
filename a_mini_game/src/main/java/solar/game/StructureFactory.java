package solar.game;

import solar.engine.GameObject;
import solar.engine.component.MonoBehavior;

public class StructureFactory {
    public static Structure createAster(Class<? extends MonoBehavior> clazz) {
        boolean flag = false;
        for (Class<?> implementedInterface : clazz.getInterfaces()) {
            if (implementedInterface.equals(AsterComponent.class)) {
                flag = true;
                break;
            }
        }
        if (!flag) return (Structure) new GameObject().addComponent(Structure.class);
        Structure structure = (Structure) new GameObject().addComponent(Structure.class);
        structure.parentAsters.add((AsterComponent) new GameObject().addComponent(clazz));
        return structure;
    }
}

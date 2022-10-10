package 装饰着模式;

/**
 * 三个装饰着  分别是： 面包，蔬菜，奶油
 */
public class Decorators {
    //面包类
    public static class Bread extends Food {

        private Food basic_food;

        public Bread(Food basic_food) {
            this.basic_food = basic_food;
        }

        public String make() {
            System.out.println("被装饰了一次");
            return basic_food.make()+"+面包";
        }
    }

    //奶油类
    public static class Cream extends Food {

        private Food basic_food;

        public Cream(Food basic_food) {
            this.basic_food = basic_food;
        }

        public String make() {
            System.out.println("被装饰了一次");
            return basic_food.make()+"+奶油";
        }
    }

    //蔬菜类
    public static class Vegetable extends Food {

        private Food basic_food;

        public Vegetable(Food basic_food) {
            this.basic_food = basic_food;
        }

        public String make() {
            System.out.println("被装饰了一次");
            return basic_food.make()+"+蔬菜";
        }

    }

}

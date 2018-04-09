package cn.gtmap.landsale.core.service.impl;

/**
 * Created by liushaoshuai on 2018/3/12.
 */
abstract class Outer {

   /* public void doSomethings(){
        Inner inner = new  Inner();
    }
    public static class Inner{
        public Inner() {
            System.out.println("init Inner!");
        }
    }*/
    abstract float getFloat();
    abstract class Outer2 extends Outer{
        float i = 1;
        @Override
        float getFloat() {
            return i;
        }
    }
    public static void main(String[] args) {


    }
}

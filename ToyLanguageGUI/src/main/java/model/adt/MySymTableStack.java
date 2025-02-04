package model.adt;

import model.value.IValue;

public class MySymTableStack extends MyStack<MyIMap<String, IValue>> {

    public MySymTableStack deepcopy() {
        MySymTableStack newStack = new MySymTableStack();
        MyStack<MyIMap<String, IValue>> tempStack = new MyStack<>();

        while (!this.stack.empty())
            tempStack.push(this.stack.pop());

        while (!tempStack.isEmpty()) {
            stack.push(tempStack.peek());
            newStack.push(tempStack.pop().clone());
        }

        return newStack;
    }
}

import React from 'react';
import TodoItem from "./TodoItem.jsx";
import {useTodos} from "../../contexts/TodoContext.jsx";

const Index = () => {
    const {todos} = useTodos();
    return (
            <div>
                <h2 className={"text-3xl text-center my-4"}>Todo List</h2>
                {todos.map(todo => (
                    <TodoItem key={todo.id} todo={todo}/>
                ))}
            </div>
    );
};

export default Index;
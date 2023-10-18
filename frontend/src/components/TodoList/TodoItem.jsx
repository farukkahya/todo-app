import React, {useState} from 'react';
import {FaPen, FaTrash, FaBars} from "react-icons/fa";
import {useTodos} from "../../contexts/TodoContext.jsx";
import {FaCheck, FaX} from "react-icons/fa6";

const TodoItem = ({todo}) => {
    const {deleteTodoById, setIsError, editTodoById,todoCheck} = useTodos()
    const [isOpen, setIsOpen] = useState(false)
    const [isUpdateText, setIsUpdateText] = useState(todo.todo)
    const [isCompleted, setIsCompleted] = useState(todo.completed)
    const handleDelete = () => {
        deleteTodoById(todo.id)
        setIsError("")
    }
    const handleUpdateChange = (e) => {
        e.preventDefault()
        setIsUpdateText(e.target.value)
    }
    const handleUpdate = () => {
        todo.todo = isUpdateText
        todo.completed = isCompleted
        editTodoById(todo.id, todo)
        setIsOpen(!isOpen)
        setIsError("")
    }

    return (
        <div className={"flex justify-between border-2 p-2 text-xl mb-2 w-[544px] items-center"}>
            <h3 className={"flex items-center gap-4 capitalize"}
            ><FaBars/> <span className={`${todo.completed && "line-through"}`}>{!isOpen && todo.todo}</span> {isOpen && (
                <>
                    <input autoFocus={isOpen} onChange={handleUpdateChange} value={`${isUpdateText}`} type="text"/>
                    <button onClick={handleUpdate}><FaCheck className={"text-green-600 hover:text-green-400"}/></button>
                    <input type="checkbox" onChange={() => setIsCompleted(!isCompleted)} checked={isCompleted}/>
                    
                </>
            )}</h3>
            <div className={"flex justify-center items-center gap-4"}>
                <button onClick={() => setIsOpen(!isOpen)}><FaPen className={"text-yellow-500 hover:text-yellow-300"}/>
                </button>
                <button onClick={handleDelete}><FaTrash
                    className={"transition-colors text-red-700 hover:text-red-400"}/></button>
            </div>

        </div>
    );
};

export default TodoItem;
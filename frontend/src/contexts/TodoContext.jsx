import {createContext, useContext, useState} from "react";
import axios from "axios";
import {toast} from "react-toastify"
const TodoContext = createContext();
export const TodoContextProvider =  ({children}) => {
    const [todos, setTodos] = useState([])
    const [isError, setIsError] = useState("")
    const [isSuccess, setIsSuccess] = useState("")

    axios.get("http://localhost:8080/api/v1/todos").then(res => setTodos(res.data))
    const addTodo = (todo) => {
        axios.post("http://localhost:8080/api/v1/create-todo", {
            "todo": todo
        })
            .then(response => console.log(response.data))
            .catch((e) => setIsError(e.response.data))
    }
    const deleteTodoById = (id) => {
        axios.delete(`http://localhost:8080/api/v1/delete-todo/${id}`)
            .then(response => setIsSuccess(response.data))
            .catch((e) => setIsError(e.response.data))
    }
    const editTodoById = (id,todo) => {
        axios.put(`http://localhost:8080/api/v1/update-todo/${id}`,{
            "todo" : todo.todo,
            "completed" : todo.completed
        }).then((response) => setIsSuccess(response.data))
            .catch((e) => setIsError(e.response.data))
    }

    const values = {
        todos,
        addTodo,
        setIsError,
        isError,
        deleteTodoById,
        isSuccess,
        setIsSuccess,
        editTodoById,
    }
    return (
        <TodoContext.Provider value={values}>{children}</TodoContext.Provider>
    );
}
export const useTodos = () => {
    const context = useContext(TodoContext);
    if (context === undefined) {
        throw new Error(`useTodos must be used within a TodoContextProvider`);
    }
    return context;
};
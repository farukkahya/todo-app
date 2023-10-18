import {FaBook} from "react-icons/fa";
import {useTodos} from "../contexts/TodoContext.jsx";
import {useState} from "react";
import {FaX, FaCheck} from "react-icons/fa6";

const TodoInput = () => {
    const {addTodo, setIsError,isError, isSuccess} = useTodos();
    const [todoText, setTodoText] = useState("")
    const handleCLick = () => {
        addTodo(todoText)
        setIsError("")
        setTodoText("")
    }
    const handleChange = (e) =>{
        setTodoText(e.target.value)
    }
    return (
        <div>
            <h1 className={"text-4xl text-center my-4 font-semibold"}>TodoInput</h1>
            {
                isError && <div className={"w-full flex justify-center items-center gap-2 text-md font-semibold bg-red-300 text-red-900 py-1.5 my-1 rounded"}><FaX/>{isError}</div>
            }
            {
                isSuccess && <div className={"w-full flex justify-center items-center gap-2 text-md font-semibold bg-green-300 text-green-900 py-1.5 my-1 rounded"}><FaCheck/>{isSuccess}</div>
            }
            <div className={"border-2 p-3 text-xl"}>
                <div className={"flex justify-center items-center border-2 rounded-md"}>
                    <button className={"rounded-l-md bg-cyan-500 p-2.5"} disabled>
                        <FaBook className={"text-xl text-white"}/>
                    </button>
                    <input
                        onChange={handleChange}
                        value={todoText}
                        className={"outline-0 py-1 px-3 w-[30rem]"}
                        type="text" placeholder={"New Todo"}/>
                </div>
                <button
                    onClick={handleCLick}
                    className={"w-full text-white p-1.5 rounded-md mt-2 bg-cyan-500"}>Add new task</button>
            </div>
        </div>
    );
};

export default TodoInput;
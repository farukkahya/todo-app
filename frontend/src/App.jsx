import TodoInput from "./components/TodoInput.jsx";
import TodoList from "./components/TodoList/index.jsx";

function App() {
    return (
        <div className={"h-[100dvh] flex flex-col justify-center items-center"}>
            <TodoInput/>
            <TodoList />
        </div>
    )
}

export default App

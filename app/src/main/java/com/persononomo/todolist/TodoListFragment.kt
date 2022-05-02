package com.persononomo.todolist

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.persononomo.todolist.databinding.TodoListBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TodoListFragment : Fragment() {

    private var _binding: TodoListBinding? = null
    private var list: ArrayList<Todo> = ArrayList<Todo>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewOfLayout: View

    fun getTodoFromCursor(cursor: Cursor): Todo {
        return Todo(cursor.getString(0), cursor.getString(1))
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = TodoListBinding.inflate(inflater, container, false)
        viewOfLayout = binding.root
        val listView: ListView = viewOfLayout.findViewById<ListView>(R.id.listview_todos)

        list = getTodoList()

        val todoList = mapTodoList()
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            viewOfLayout.context,
            android.R.layout.simple_list_item_1, todoList
        )
        listView.setAdapter(adapter)

        val onItemClickListener = AdapterView.OnItemClickListener() { _, _, position, _ ->
            val todo = list[position]
            activity?.contentResolver?.delete(TodoListContentProvider.CONTENT_URI,
                "text = ?", arrayOf(todo.text))
            list = getTodoList()
            val todoList = mapTodoList()
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                viewOfLayout.context,
                android.R.layout.simple_list_item_1, todoList
            )
            listView.setAdapter(adapter)
        }
        listView.setOnItemClickListener(onItemClickListener)
        return viewOfLayout

    }

    private fun mapTodoList() = list.map { "- " + it.text }

    private fun getTodoList(): ArrayList<Todo> {
        val todoList = ArrayList<Todo>()
        val cursor: Cursor = activity?.contentResolver?.query(
            TodoListContentProvider.CONTENT_URI,
            arrayOf("*"),
            null, null, null
        )!!;
        while (cursor.moveToNext()) {
            todoList.add(getTodoFromCursor(cursor))
        }

        return todoList
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
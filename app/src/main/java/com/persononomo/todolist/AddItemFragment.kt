package com.persononomo.todolist

import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.persononomo.todolist.databinding.AddItemBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddItemFragment : Fragment() {

    private var _binding: AddItemBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewOfLayout: View

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = AddItemBinding.inflate(inflater, container, false)
        viewOfLayout = binding.root
    return viewOfLayout

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSaveItem.setOnClickListener {
            val values = ContentValues()
            val todo = viewOfLayout.findViewById<EditText>(R.id.textview_second).getText().toString()
            values.put(TodoListContentProvider.text, todo);
            print(todo)
            activity?.contentResolver?.insert(TodoListContentProvider.CONTENT_URI, values);
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
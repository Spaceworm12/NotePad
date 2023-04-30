//package ru.lesson.fragmentsample.presentation.recycler
//
//import android.os.Bundle
//import android.system.Os
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.core.view.isVisible
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.homework.R
//import com.example.homework.databinding.FragmentPreviewBinding
//import com.example.homework.presentation.detail.DetailNoteFragment
//import com.example.homework.presentation.model.NoteModel
//import com.example.homework.presentation.recycler.RecyclerEvent
//import com.example.homework.presentation.recycler.adapter.PreviewAdapter
//import com.google.android.material.dialog.MaterialAlertDialogBuilder
//
//
//class RecyclerFragment : Fragment() {
//
//    private var _binding: FragmentPreviewBinding? = null
//    private val binding get() = _binding!!
//
//    //Получаем экземпляр нашей ViewModel. "by lazy" говорит о том, что экземпляр мы получим тогда,
//    // когда первый раз вызовем(используем ссылку) viewModel
//    private val viewModel: RecyclerViewModel by lazy {
//        ViewModelProvider(this)[RecyclerViewModel::class.java]
//    }
//    private val adapter = PreviewAdapter { index, note ->
//        onShowDeleteDialog(index, note)
//    }
//    private val adapter = PreviewAdapter { exampleModelName ->
//        requireActivity()
//            .supportFragmentManager
//            .beginTransaction()
//            .add(
//                R.id.fragment_container,
//                DetailNoteFragment.newInstance(exampleModelName)
//            )
//            .addToBackStack("")
//            .commit()
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentPreviewBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        //Отправляем во ViewModel Event(событие) о необходимости загрузки данных
//        // submitUIEvent - название нам говорит о том же
//        // RecyclerEvent.GetItems - во ViewModel мы прописали, что будем делать, когда придет этот ивент
//        viewModel.submitUIEvent(RecyclerEvent.GetItems)
//
//        binding.recView.layoutManager = LinearLayoutManager(requireActivity())
//        binding.recView.adapter = adapter
//
//        //Подписываемся на просмотр изменений в нашей MutableLiveData(RecyclerViewState())
//        //Как только будут какие-либо изменения мы получим их здесь
//        viewModel.viewStateObs.observe(viewLifecycleOwner) { state ->
//
//            //Устанавливаем видимость элементов в зависимости от загрузки
//            binding.loader.isVisible = state.isLoading
//            binding.fabAddItem.isVisible = !state.isLoading
//            binding.recView.isVisible = !state.isLoading
//
//            adapter.submitList(state.itemList)
//        }
//
//        binding.fabAddItem.setOnClickListener {
//            //Еще один ивент для ViewModel
//            viewModel.submitUIEvent(
//                RecyclerEvent.AddItem(
//                    NoteModel(
//                        id = -1L,
//                        name = "???",
//                        description = "???"
//                    )
//                )
//            )
//        }
//
//    }
//    private fun onShowDeleteDialog(index: Int, note: NoteModel) {
//        MaterialAlertDialogBuilder(requireContext())
//            .setMessage("Delete this note?")
//            .setCancelable(true)
//            .setPositiveButton("Yes") { _, _ ->
//                .remove(note)
//                adapter.notifyItemRemoved(index)
//            }
//            .setNegativeButton("No") { _, _ -> }
//            .create()
//            .show()
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}

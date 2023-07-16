package com.example.homework.presentation.recycler

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homework.data.models.model.app.AppNotes
import com.example.homework.data.models.model.noterepository.Repository
import com.example.homework.data.models.model.noterepository.RepositoryImplement
import com.example.homework.presentation.composefutures.FIRST_THEME
import com.example.homework.presentation.composefutures.THEME_CODE
import com.example.homework.presentation.model.Mapper
import com.example.homework.presentation.model.NoteModel
import com.example.homework.util.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo


class ListViewModel(
    private val repo: Repository = RepositoryImplement(AppNotes.dao(), AppNotes.getDb())
) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val _viewState = MutableLiveData(ListViewState())
    val viewStateObs: LiveData<ListViewState> get() = _viewState
    var viewState: ListViewState
        get() = _viewState.value!!
        set(value) {
            _viewState.value = value
        }

    init {
        viewState = viewState.copy(
            currentTheme = AppNotes.getSettingsTheme().getInt(THEME_CODE, FIRST_THEME)
        )
    }

    fun submitUIEvent(event: ListEvents) {
        handleUIEvent(event)
    }

    private fun handleUIEvent(event: ListEvents) {
        setEvents(event)
    }

    private fun setEvents(event: ListEvents) {
        when (event) {
            is ListEvents.GetNotes -> getListNotes()
            is ListEvents.DeleteNote -> deleteNote(id = event.id)
            is ListEvents.DeleteNoteModel -> deleteNoteModel(event.note)
            is ListEvents.DeleteAll -> deleteAll()
            is ListEvents.SaveCurrentNote -> viewState = viewState.copy(currentNote = event.note)
            is ListEvents.ShowChangeDialog -> viewState =
                viewState.copy(isShowChangeDialog = event.itsShow)
            is ListEvents.ShowDateAddDialog -> viewState = viewState.copy(isShowDateAddDialog = event.itsShow)
            is ListEvents.SaveUserDate -> changeDate(note=event.note,date = event.note.date, id = event.note.id)
            is ListEvents.ChangeTheme -> setTheme(event.themeCode)
            is ListEvents.ShowDeleteDialog -> viewState =
                viewState.copy(isShowDeleteDialog = event.itsShow, deletableNoteId = event.id)
            is ListEvents.ClearAll -> viewState =
                viewState.copy(isShowDeleteAllDialog = event.itsShow)
            is ListEvents.ShowSettingsDialog -> viewState =
                viewState.copy(isShowSettingsDialog = event.itsShow)
        }
    }


    private fun getListNotes() {
        viewState = viewState.copy(isLoading = true)
        repo.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when (result) {
                    is Resource.Loading -> {}
                    is Resource.Data -> {
                        viewState = viewState.copy(
                            notesList = Mapper.transformToPresentation(result.data ?: emptyList()),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        viewState =
                            viewState.copy(isLoading = false, errorText = "error")
                    }
                }
            }
            .addTo(disposables)
    }

    private fun deleteNote(id: Long) {
        repo.delete(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when (result) {
                    is Resource.Loading -> viewState = viewState.copy(isLoading = true)
                    is Resource.Data -> getListNotes()
                    is Resource.Error -> viewState =
                        viewState.copy(isLoading = false, errorText = "err")
                }
            }
            .addTo(disposables)
    }

    private fun deleteNoteModel(note: NoteModel) {
        repo.delete(note.id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when (result) {
                    is Resource.Loading -> viewState = viewState.copy(isLoading = true)
                    is Resource.Data -> getListNotes()
                    is Resource.Error -> viewState =
                        viewState.copy(isLoading = false, errorText = "err")
                }
            }
            .addTo(disposables)
    }

    private fun deleteAll() {
        repo.deleteAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when (result) {
                    is Resource.Loading -> viewState = viewState.copy(isLoading = true)
                    is Resource.Data -> getListNotes()
                    is Resource.Error -> {
                        viewState = viewState.copy(
                            isLoading = false,
                            errorText = result.error.message ?: ""
                        )
                    }
                }
            }
            .addTo(disposables)
    }

    private fun changeDate(note:NoteModel,date: String, id: Long) {
        repo.changeDate(date, id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                when (result) {
                    is Resource.Loading -> viewState = viewState.copy(isLoading = true)
                    is Resource.Data -> getListNotes()
                    is Resource.Error -> viewState =
                        viewState.copy(isLoading = false, errorText = result.error.message ?: "")
                }
            }.addTo(disposables)
    }

    private fun setTheme(theme: Int) {
        AppNotes.getSettingsTheme().edit().putInt(THEME_CODE, theme).apply()
        viewState = viewState.copy(currentTheme = theme, isShowSettingsDialog = false)
    }

    override fun onCleared() {
        disposables.clear()
    }

}






package com.example.homework.data.models.model.noteRepository

import com.example.homework.data.models.model.db.Dao
import com.example.homework.data.models.model.db.Db
import com.example.homework.data.models.model.db.entity.NoteEntity
import com.example.homework.util.Resource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.internal.operators.single.SingleInternalHelper.toObservable
import io.reactivex.schedulers.Schedulers

class RepositoryImplement(private val dao: Dao, private val db: Db) : Repository {

    override fun getAll(): Observable<Resource<List<NoteEntity>>> {
        return dao.getAll()
            .map<Resource<List<NoteEntity>>> { Resource.Data(it) }
            .onErrorReturn {Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())

    }
    override fun create(entity: NoteEntity): Observable<Resource<Long>> {
        return dao.create(entity)
            .toObservable()
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }
    override fun delete(id: Long): Observable<Resource<Unit>> {
        return dao.delete(id)
            .andThen(Observable.just(Resource.Success))
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }
    override fun deleteAll(): Observable<Resource<Unit>> {
             return db.clearAllTables()
                .onErrorReturn { Resource.Error(it) }
                .startWith(Resource.Loading)
                .subscribeOn(Schedulers.io())
        }

    override fun changeDate(date: String, id: Long): Observable<Resource<Long>> {
        return dao.changeDate(date,id)
            .toObservable()
            .andThen
            .onErrorReturn { Resource.Error(it) }
            .startWith(Resource.Loading)
            .subscribeOn(Schedulers.io())
    }
    }






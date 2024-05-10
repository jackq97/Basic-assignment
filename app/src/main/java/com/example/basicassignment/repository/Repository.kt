package com.example.basicassignment.repository

import com.example.basicassignment.model.VideoData
import com.example.basicassignment.util.Resource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(private val supabase: SupabaseClient) {

    fun getVideoData(): Flow<Resource<List<VideoData>>> {
        return flow {
            emit(Resource.Loading())
            val result =  supabase.from("video").select().decodeList<VideoData>()
            emit(Resource.Success(result))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }

    fun getFilteredVideoData(searchText: String): Flow<Resource<List<VideoData>>> {
        return flow {
            emit(Resource.Loading())
            val searchResult = supabase.from("video").select(columns = Columns.list("*")) {
                filter {
                    ilike("title", "%$searchText%")
                }
            }.decodeList<VideoData>()
            emit(Resource.Success(searchResult))
        }.catch {
            emit(Resource.Error(it.message.toString()))
        }
    }
}
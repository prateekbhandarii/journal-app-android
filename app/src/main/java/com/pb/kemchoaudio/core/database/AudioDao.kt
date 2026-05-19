package com.pb.kemchoaudio.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.pb.kemchoaudio.core.database.audio.AudioEntity
import com.pb.kemchoaudio.core.database.audio_topic_relation.AudioTopicCrossRef
import com.pb.kemchoaudio.core.database.audio_topic_relation.AudioWithTopics
import com.pb.kemchoaudio.core.database.topic.TopicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AudioDao {

    @Query("SELECT * FROM audioentity ORDER BY recordedAt DESC")
    fun observeAudios(): Flow<List<AudioWithTopics>>

    @Query("SELECT * FROM topicentity ORDER BY topic ASC")
    fun observeTopics(): Flow<List<AudioWithTopics>>

    @Query("select * from topicentity where topic like '%' || :query || '%' order by topic ASC")
    fun searchTopics(query: String): Flow<List<TopicEntity>>

    @Insert
    suspend fun insertAudio(audioEntity: AudioEntity): Long

    @Upsert
    suspend fun upsertTopic(topic: TopicEntity)

    @Insert
    suspend fun insertAudioTopicCrossRef(crossRef: AudioTopicCrossRef)

    @Transaction
    suspend fun insertAudioWithTopics(audioWithTopics: AudioWithTopics) {
        val audioId = insertAudio(audioWithTopics.audio)

        audioWithTopics.topics.forEach { topic ->
            upsertTopic(topic)
            insertAudioTopicCrossRef(
                crossRef = AudioTopicCrossRef(
                    id = audioId.toInt(),
                    topic = topic.topic
                )
            )
        }
    }
}
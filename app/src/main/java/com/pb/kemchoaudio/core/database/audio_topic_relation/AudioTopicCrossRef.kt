package com.pb.kemchoaudio.core.database.audio_topic_relation

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.pb.kemchoaudio.core.database.audio.AudioEntity
import com.pb.kemchoaudio.core.database.topic.TopicEntity

@Entity(primaryKeys = ["audioId", "topic"])
data class AudioTopicCrossRef(
    val id: Int,
    val topic: String
)

data class AudioWithTopics(
    @Embedded val audio: AudioEntity,
    @Relation(
        parentColumn = "audioId",
        entityColumn = "topic",
        associateBy = Junction(AudioTopicCrossRef::class)
    )
    val topics: List<TopicEntity>
)
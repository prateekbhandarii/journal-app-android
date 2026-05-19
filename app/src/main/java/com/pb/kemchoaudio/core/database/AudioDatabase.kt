package com.pb.kemchoaudio.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pb.kemchoaudio.core.database.audio.AudioEntity
import com.pb.kemchoaudio.core.database.audio.FloatListTypeConverter
import com.pb.kemchoaudio.core.database.audio_topic_relation.AudioTopicCrossRef
import com.pb.kemchoaudio.core.database.topic.TopicEntity

@Database(
    entities = [AudioEntity::class, TopicEntity::class, AudioTopicCrossRef::class],
    version = 1
)
@TypeConverters(
    FloatListTypeConverter::class,
    //MoodUiTypeConverter::class
)
abstract class AudioDatabase : RoomDatabase() {
    abstract val audioDao: AudioDao
}
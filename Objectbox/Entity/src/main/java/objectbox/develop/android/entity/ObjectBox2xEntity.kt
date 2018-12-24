package objectbox.develop.android.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class ObjectBox2xEntity(@Id var id: Long = 0, var name: String, var age: String)

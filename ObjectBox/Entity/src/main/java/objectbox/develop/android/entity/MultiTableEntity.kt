package objectbox.develop.android.entity

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
class SchoolEntity {
    @Id
    var id: Long = 0
    lateinit var student: ToMany<StudentEntity>
}

@Entity
class StudentEntity(@Id var id: Long = 0, @Backlink var name: String = "")

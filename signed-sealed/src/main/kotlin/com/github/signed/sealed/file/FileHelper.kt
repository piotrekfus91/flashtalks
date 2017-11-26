package com.github.signed.sealed.file

import java.io.*
import java.nio.file.Path

object FileHelper {
    fun <T : Serializable> save(obj: T, path: Path) {
        FileOutputStream(path.toFile()).use { fileOutputStream ->
            ObjectOutputStream(fileOutputStream).use { objectOutputStream ->
                objectOutputStream.writeObject(obj)
            }
        }
    }

    fun <T : Serializable> read(path: Path, clazz: Class<T>): T {
        FileInputStream(path.toFile()).use { fileInputStream ->
            ObjectInputStream(fileInputStream).use { objectInputStream ->
                return objectInputStream.readObject() as T
            }
        }
    }
}

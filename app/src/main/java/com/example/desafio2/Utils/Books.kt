package com.example.desafio2.Utils

import com.example.desafio2.Models.Author
import com.example.desafio2.Models.Book

object Books {
    fun initBookArray(): MutableList<Book> {
        var books: MutableList<Book> = mutableListOf()
        books.add(
            Book(
                "Los pilares de la tierra",
                Author("Ken Follet",
                        "Escritor británico de novelas de suspense e históricas.",
                    "https://upload.wikimedia.org/wikipedia/commons/4/4c/Ken_Follett_official.jpg"),
                "Historica",
                "La novela describe el desarrollo de la arquitectura gótica a partir de su precursora, la arquitectura románica, y las vicisitudes del priorato de Kingsbridge",
                "Editorial: Macmillan, Gales, 1989, 976 paginas",
                "https://images-na.ssl-images-amazon.com/images/I/91+I2lzZ4JL.jpg"
            )
        )
        books.add(
            Book(
                "El psicoanalista",
                Author("John Katzenbach",
                    "Escritor estadounidense, sus novelas han sido nominadas a dos premios.",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/John_Katzenbach_2.jpg/220px-John_Katzenbach_2.jpg"),
                "Thriller",
                "La historia, que pone a prueba la capacidad del protagonista para evitar su suicidio frente a la presión de un desconocido",
                "Editorial: Ballantine, Nueva York, 2002, 432 paginas",
                "https://images-na.ssl-images-amazon.com/images/I/41Mx7lTSCzL._SX325_BO1,204,203,200_.jpg"
            )
        )
        books.add(
            Book(
                "Eragon",
                Author("Christopher Paolini",
                    "Es un escritor estadounidense conocido por su saga de libros El legado, que se compone de cuatro novelas: Eragon, Eldest, Brisingr y Legado.",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/2/23/Christopher_Paolini_2019.jpg/220px-Christopher_Paolini_2019.jpg"),
                "Fantasia",
                "En el reino legendario de Alagaësia la guerra se está gestando. Los Jinetes protectores de la paz del Imperio y los únicos capaces de controlar a los inteligentes dragones, se han extinguido o han pasado a formar parte de las tropas del malvado rey Galbatorix.",
                "Editorial: Roca Editorial, Nueva York, 2003, 544 paginas",
                "https://upload.wikimedia.org/wikipedia/commons/8/83/Christopher_Paolini%2C_Eragon_1.jpg"
            )
        )
        books.add(
            Book(
                "MOMO",
                Author("Michael Ende",
                    "Escritor alemán de literatura infantil y fantástica.",
                    "https://historia-biografia.com/wp-content/uploads/2018/06/Biograf%C3%ADa-87.jpg"),
                "Fantasia",
                "Un clásico inolvidable de la literatura juvenil, de Michael Ende.",
                "Editorial: Alfaguara, Alemania, 1973",
                "https://images-na.ssl-images-amazon.com/images/I/A1bbRMSKhRL.jpg"
            )
        )
        books.add(
            Book(
                "El principito",
                Author("Antoine de Saint-Exupéry",
                    "Aviador y escritor Frances.",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7f/11exupery-inline1-500.jpg/220px-11exupery-inline1-500.jpg"),
                "Fantasia",
                "Novela corta y la obra más famosa del escritor y aviador francés Antoine de Saint-Exupéry.",
                "Editorial: Gallimard, Estados Unidos, 1943",
                "https://imagessl4.casadellibro.com/a/l/t7/94/9788478887194.jpg"
            )
        )
        return books
    }
}
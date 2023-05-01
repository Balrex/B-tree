# Контейнер Btree_map

# Задача: 
реализовать ассоциативный массив (контейнер Вtree_map) на основе B-дерева. Каждый узел дерева должен содержать набор пар ключ-значение. Пользователь должен иметь возможность: 

  -- получить значение по ключу;
  
  --изменить значение по ключу;
  
  --добавить в контейнер новую пару.
# Указания по выполнению:
- лабораторная работа состоит из двух файлов:

  -- ВtreeМap – описание класса Вtree_map, прототипы методов, реализация методов;
  
  -- ТestМap – тестирование и проверка возможностей класса;
 - создать вспомогательную шаблонную структуру node, содержащую два поля (контейнер с парами {ключ, значение}, контейнер с указателями на дочерние структуры node);
 - класс ВtreeМap содержит три шаблонных параметра: тип ключа, тип значения, тип критерия сравнения (по умолчанию comparator);
 - - класс ВtreeМap содержит два поля (указатель на узел, являющийся корнем дерева; параметр t ≥ 2);
 - класс ВtreeМap должен содержать следующие методы:
    -- конструктор;
    -- конструктор копирования;
    --проверка на пустоту;
    --удаление всех элементов;
    --добавление пары ключ-значение;
    --поиск по ключу;
    --изменение по ключу.

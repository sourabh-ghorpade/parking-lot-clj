(ns parking-lot.input.command-argument-option)

(defn create-valid-option [command argument result]
  {:command   command
   :arguments argument
   :result    result
   :valid?    true})

(defn create-invalid-option [result]
  {:result result
   :valid? false})

(defn result [option]
  (:result option))

(defmacro if-valid [option function]
  `(if (:valid? ~option)
    (~function ~option)
    ~option))

(ns parking-lot.input.command-argument-option)

(defn create-valid-option [command arguments result]
  {:command   command
   :arguments arguments
   :result    result
   :valid?    true})

(defn create-invalid-option [result]
  {:result result
   :valid? false})

(defmacro if-valid [option function]
  `(if (:valid? ~option)
     (~function ~option)
     ~option))

(defn result [option]
  (:result option))

(defn command [option]
  (:command option))

(defn arguments [option]
  (:arguments option))

(defn create-valid-with-result [option result]
  (create-valid-option (command option)
                       (arguments option)
                       result))


(ns parking-lot.input.parser
  (:require [clojure.string :as str]
            [parking-lot.input.command-argument-option :as option]))

(defn parse [input]
  (if input
    (let [parsed-input (str/split input #" ")]
      (option/create-valid-option (first parsed-input) (rest parsed-input) ""))
    (option/create-invalid-option "Input is empty")))

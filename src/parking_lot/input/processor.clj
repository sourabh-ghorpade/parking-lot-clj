(ns parking-lot.input.processor
  (:require [parking-lot.input.parser :as parser]
            [parking-lot.command.validator :as validator]
            [parking-lot.command.executor :as executor]
            [parking-lot.input.command-argument-option :refer :all]))

(defn process [input]
  (-> (parser/parse input)
      (if-valid validator/validate)
      (if-valid executor/execute)
      (result)))

(ns parking-lot.input.processor
  (:require [parking-lot.input.parser :as parser]
            [parking-lot.command.validator :as validator]
            [parking-lot.command.executor :as executor]))

(defn process [input]
  (-> input
      (parser/parse)
      (validator/validate)
      (executor/execute)
      (:result)
      (println)))

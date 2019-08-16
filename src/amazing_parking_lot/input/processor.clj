(ns amazing-parking-lot.input.processor
  (:require [amazing-parking-lot.input.parser :as parser]
            [amazing-parking-lot.command.validator :as validator]
            [amazing-parking-lot.command.executor :as executor]
            [amazing-parking-lot.input.command-argument-option :refer :all]))

(defn process [input]
  (-> (parser/parse input)
      (if-valid validator/validate)
      (if-valid executor/execute)
      (result)))

(ns parking-lot.input.processor
  (:require [parking-lot.input.parser :as parser]
            [parking-lot.command.validator :as validator]
            [parking-lot.command.executor :as executor]
            [parking-lot.input.command-argument-option :as option]))

(defn process [input]
  (-> (parser/parse input)
      (option/if-valid validator/validate)
      (option/if-valid executor/execute)
      :result))

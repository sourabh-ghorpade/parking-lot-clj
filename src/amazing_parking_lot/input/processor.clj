(ns amazing-parking-lot.input.processor
  (:require [amazing-parking-lot.input.parser :as parser]
            [amazing-parking-lot.command.validator :as validator]
            [amazing-parking-lot.command.executor :as executor]
            [amazing-parking-lot.input.command-argument-option :as option]))

(defn process [input parking-lot]
  (let [parsed-input-command (option/add-parking-lot (parser/parse input) parking-lot)]
    (-> parsed-input-command
        (option/if-valid validator/validate)
        (option/if-valid executor/execute))))

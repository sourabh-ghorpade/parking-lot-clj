(ns amazing-parking-lot.command.validator-test
  (:require [clojure.test :refer :all]
            [amazing-parking-lot.input.command-argument-option :as option-builder]
            [amazing-parking-lot.command.validator :refer [validate]]
            [amazing-parking-lot.input.command-argument-option :as option]))

(defn- validations-failed? [validated-option]
  (and (not (:valid? validated-option))
       (= "Invalid Command/Arguments" (option/result validated-option))))

(deftest validate-test
  (testing "When the command is a valid command"
    (testing "and when the argument count is correct"
      (testing "it returns a valid option"
        (let [valid-option (option-builder/create-valid-option "create_parking_lot" ["6"] "")
              validated-option (validate valid-option)]
          (is (true? (:valid? validated-option))))))
    (testing "and when the argument count is incorrect"
      (testing "it returns a invalid option"
        (let [valid-option (option-builder/create-valid-option "create_parking_lot" [] "")
              validated-option (validate valid-option)]
          (is (validations-failed? validated-option))))))
  (testing "and when the command is not a valid command"
    (testing "it returns an invalid option"
      (let [valid-option (option-builder/create-valid-option "random_command" "6" "")
            validated-option (validate valid-option)]
        (is (validations-failed? validated-option))))))

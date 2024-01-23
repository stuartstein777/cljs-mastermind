(ns exfn.subscriptions
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :current-guess-number
 (fn [db _]
   (:current-guess-number db)))

(rf/reg-sub
  :current-guess
  (fn [db _]
    (:current-guess db)))

(rf/reg-sub
  :guesses
  (fn [db _]
    (:guesses db)))

(rf/reg-sub
  :clues
  (fn [db _]
    (:clues db)))

(rf/reg-sub
  :solution
  (fn [db _]
    (:solution db)))

(rf/reg-sub
  :game-won?
  (fn [db _]
    (:game-won? db)))

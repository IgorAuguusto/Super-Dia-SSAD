import { create } from 'zustand'

type Counter = {
  count: number
  increaseCounter: () => void
  decreaseCounter: () => void
  setCounter: (value: number) => void
}

export const useCounter = create<Counter>()((set) => ({
  count: 0,
  increaseCounter: () => set((state) => ({ count: state.count + 1 })),
  decreaseCounter: () => set((state) => ({count: state.count - 1})),
  setCounter: (value: number) => set(() => ({count: value}))
}))